package ru.pavel2107.neostoreRest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import ru.pavel2107.neostoreRest.client.AuthorizedRestOperations;
import ru.pavel2107.neostoreRest.controller.PetControllerResources;
import ru.pavel2107.neostoreRest.controller.UserControllerResources;
import ru.pavel2107.neostoreRest.controller.UtilControllerResources;
import ru.pavel2107.neostoreRest.model.Breed;
import ru.pavel2107.neostoreRest.model.Pet;
import ru.pavel2107.neostoreRest.model.User;
import ru.pavel2107.neostoreRest.utils.ErrorInfo;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by lenovo on 12.12.2015.
 *  Created by lenovo on 15.12.2015.
 * http://www.journaldev.com/2552/spring-restful-web-service-example-with-json-jackson-and-client-program
 * http://www.journaldev.com/8718/spring-4-security-login-logout-inmemory-example
 * http://www.journaldev.com/2736/spring-mvc-security-example-using-in-memory-userdetailsservice-and-jdbc-authentication
 * http://howtodoinjava.com/2015/02/20/spring-restful-client-resttemplate-example/
 *
 */


//
// Основной обработчик клиентской программы.
// Т.к. программа является клиентской, то вызовы REST-сервисов сделаны именно через
// RestTemplate
//
@Controller( "clientRootController")
public class RootController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(RootController.class);

    //
    // адрес сервисов
    //
    private static final String REST_SERVICE_URI = "http://localhost:8070/neoStore";
    private String restServiceUri  = "http://localhost:8070/neoStore";

    public String getRestServiceUri() {
        return restServiceUri;
    }

    public void setRestServiceUri(String restServiceUri) {
        this.restServiceUri = restServiceUri;
    }

    public AuthorizedRestOperations getRestClient() {
        return restClient;
    }

    public void setRestClient(AuthorizedRestOperations restClient) {
        this.restClient = restClient;
    }

    //
    // объект для вызова веб-сервисов
    //
    @Autowired
    AuthorizedRestOperations restClient;

    //
    // перенаправляем на index.jsp
    //
    @RequestMapping( value = "/", method = RequestMethod.GET)
    public String root()
    {
        return "index";
    }
    //
    // проверка имени пользователя.
    //
    @RequestMapping( value = "/checkname", method = RequestMethod.GET)
    public String checkName( HttpServletRequest request, ModelMap map) throws IOException
    {

        request.setCharacterEncoding("UTF-8");
        String newname = request.getParameter( "newname");
        LOG.info( "checking name=" + newname);
        //
        // запрашиваем веб-сервис о доступности имени
        //
        final String URI = getRestServiceUri() + UserControllerResources.FIND;
        Map<String, String> params = new HashMap<>();
        params.put( "username", newname);
        User user = null;
        try {
            user = restClient.getForObject(URI, User.class, params);
        }
        catch( HttpClientErrorException e){
          return processServerException( e, request, map);
        }
        //
        // если сервис ничего не вернул - имя свободно
        //
        map.put( "checkedname",
                user == null ?
                        "Имя " + newname + " свободно ":
                        "Пользователь " + newname + " уже существует");
        return "index";
    }


    //
    // Логин или регистрация пользователя.
    //
    //
    @RequestMapping( value = "/login", method = RequestMethod.POST)
    public String root( HttpServletRequest request, ModelMap map   ) throws IOException{
        request.setCharacterEncoding("UTF-8");
        //
        // достаем параметры из запроса
        //
        String action = request.getParameter( "action");
        LOG.info( "login POST. action = " + action);
        String username = request.getParameter( "username");
        String password = request.getParameter( "password");
        String register = request.getParameter( "register");
        //
        // если пользователя нет, делаем create и возвращаем пользователя
        // если пользователь есть, делаем get и получаем информацию о пользователе
        //
        String uri = null;
        User user = null;
        try {
            user = new User( null, username, password, null, 0);
            restClient.setCredentials( username, password);
            if( "on".equals( register)){
                //
                // создаем нового пользователя
                //
                uri = getRestServiceUri() + UserControllerResources.CREATE;
                user = restClient.postForObject( uri, user, User.class);
            }
            else{
                //
                // делаем поиск пользователя. этого достаточно для аутентификации
                //
                uri = getRestServiceUri() + UserControllerResources.LOGIN;
                Map<String, String> params = new HashMap<>();
                params.put( "username", username);
                user = restClient.getForObject( uri, User.class, params);
            }
            //
            // информацию о пользователе запишем в сессию
            //
            user.setPassword( password);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
        }
        catch( HttpClientErrorException e){
            return processServerException(e, request, map);
        }
        //
        // если получить информацию о пользователе получить не удалось - отправляем на страницу login
        //
        if( user == null){
            return "index";
        }
        //
        // заполняем информацию о животных и идем на страницу животных
        //
        map.put( "breeds",   getBreedList( request, map));
        map.put( "pets",     getPetsList( request, map));
        map.put( "userid",   user.getId());
        map.put( "username", user.getUsername());

        return "pets";
    }

    //
    // Логин или регистрация пользователя.
    //
    //
    @RequestMapping( value = "/logout", method = RequestMethod.POST)
    public String logout( HttpServletRequest request, ModelMap map   ) throws IOException {
        request.setCharacterEncoding("UTF-8");
        map.put( "userid", null);
        HttpSession session = request.getSession();
        session.setAttribute( "user", null);
        return "index";
    }


    //
    // просмотр страницы с животными
    //
    @RequestMapping( value = "/pets", method = RequestMethod.GET)
    public String pets( HttpServletRequest request, ModelMap map   ) throws IOException{
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter( "action");
        LOG.info( "PETS. GET. action = " + action);
        //
        // вызов рест-сервиса для удаления животного
        //
        if( "delete".equals( action)){
           String id     = request.getParameter( "id");
           final String uri = getRestServiceUri() + PetControllerResources.DELETE;
           Map<String, String> params = new HashMap<>();
           params.put( "id", id);
            try {
                HttpSession session = request.getSession();
                User user = (User)session.getAttribute( "user");
                LOG.info( "user=" + user);
                restClient.setCredentials( user.getUsername(), user.getPassword());
                restClient.delete(uri, params);
            }
            catch ( HttpClientErrorException e){
                return processServerException( e, request, map);
            }
        }
        //
        // снова заполняем данными модель и переходим на эту же страницу.
        // Пользователь в параметрах сессии остается тем же самым
        //
        map.put( "breeds", getBreedList( request, map));
        map.put( "pets", getPetsList( request, map));
        User user = (User)request.getSession(true).getAttribute("user");
        map.put( "userid", user.getId());
        map.put( "username", user.getUsername());
        return "pets";
    }

    //
    // после сохранения данных о животном переходим на страницу со списком животных методом POST
    //
    @RequestMapping( value = "/pets", method = RequestMethod.POST)
    public String petSave( HttpServletRequest request, ModelMap map   ) throws IOException{
        request.setCharacterEncoding("UTF-8");
        //
        // читаем параметры животного
        //
        String action = request.getParameter( "action");
        LOG.info( "PETS. POST. action = " + action);
        String id     = request.getParameter( "id");
        String name   = request.getParameter( "name");

        String breed  = request.getParameter( "breed");
        String bDate  = request.getParameter( "bDate");
        String sex    = request.getParameter( "sex");
        String submit = request.getParameter( "submit");
        //
        // пользователя достаем из сессии
        //
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //
        // сохраняем данные о животном
        //
        if( "Сохранить".equals( submit)) {
           LocalDate birthDate = LocalDate.parse(bDate);
           Pet pet =
              new Pet(
                    id.isEmpty() ? null : Integer.valueOf(id),
                    name,
                    Integer.parseInt(breed),
                    birthDate,
                    sex,
                    user);
           try {
             final String uri = getRestServiceUri() + PetControllerResources.SAVE;
               LOG.info( "user=" + user);
               restClient.setCredentials( user.getUsername(), user.getPassword());
               pet = restClient.postForObject( uri, pet, Pet.class);
             LOG.info( "saved pet=" + pet);
           }
           catch( HttpClientErrorException e){
               return processServerException(e, request, map);
           }
        }
        //
        // снова получаем список животных и переходим к просмотру
        //
        map.put( "breeds", getBreedList( request, map));
        map.put( "pets", getPetsList( request, map));
        map.put( "userid", user.getId());
        map.put( "username", user.getUsername());
        return "pets";
    }
    //
    // редактирование данных о животном
    //
    @RequestMapping( value = "/pet", method = RequestMethod.GET)
    public String pet( HttpServletRequest request, ModelMap map   ) throws IOException{
        request.setCharacterEncoding("UTF-8");
        //
        // получаем параметры из запроса
        //
        String action = request.getParameter( "action");
        String id     = request.getParameter( "id");
        String forwardTo = "pet";
        LOG.info( "PET. action = " + action + ", id =" + id);
        //
        // получаем пользоватея из сессии
        //
        User user = (User) request.getSession(true).getAttribute( "user");
        Pet pet = null;
        //
        // получаем данные о животном для редактирования
        //
        if( "update".equals( action)){
            try {
                final String URI = getRestServiceUri() + PetControllerResources.GET;
                Map<String, String> params = new HashMap<>();
                params.put( "id", id);
                LOG.info("user=" + user);
                restClient.setCredentials( user.getUsername(), user.getPassword());
                pet = restClient.getForObject( URI, Pet.class, params);
            }
            catch( HttpClientErrorException e){
                return processServerException( e, request, map);
            }
        }
        //
        // создаем пустышку для нового жиотного
        //
        if( "create".equals( action)){
            pet = new Pet();
            pet.setUser(user);
        }
        map.put( "breeds", getBreedList( request, map));
        map.put( "pet", pet);
        map.put( "userid", user);
        map.put( "username", user.getUsername());
        return forwardTo;
    }


    //
    // обработчик HttpClientException
    // если пришел JSon - пишем в мапу, в противном случае покажем полученную страницу
    //
    private String processServerException( HttpClientErrorException e, HttpServletRequest request, ModelMap map ) {
        String forwardTo = "index";
        try {
            ObjectMapper mapper = new ObjectMapper();
            ErrorInfo errorInfo = mapper.readValue(e.getResponseBodyAsString(), ErrorInfo.class);
            LOG.error("error: " + errorInfo.getErrCode() + "/" + errorInfo.getErrMessage());
            map.put( "error", "Ошибка : " + errorInfo.getErrCode() + "/" + errorInfo.getErrMessage());
        }
        catch ( Exception ee){
            LOG.error( "Ошибка " + ee.getMessage());
            LOG.error( "Обрабатываем ответ: " + e.getResponseBodyAsString());
        }
        return forwardTo;
    }
    //
    // получаем список животных
    //
    private List<Pet> getPetsList( HttpServletRequest request, ModelMap map){
        List<Pet>petsList = new ArrayList<>();
        try {
            String uri = getRestServiceUri() + PetControllerResources.GET_ALL;
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            LOG.info( "user=" + user);
            restClient.setCredentials( user.getUsername(), user.getPassword());
            Pet[] pets = restClient.getForObject( uri, Pet[].class);
            if( pets != null) for (Pet p : pets) petsList.add(p);
        }
        catch( HttpClientErrorException e){
            processServerException(e, request, map);
        }
        return petsList;
    }
    //
    // получаем список пород. Тут может быть две стратегии для работы со справочниками
    // 1. справочник все время читается из системы
    // 2. справочник кешируется клиентским приложением
    // Я каждый раз читаю справочник
    //
    private List<Breed> getBreedList( HttpServletRequest request, ModelMap map){
        List<Breed>breedList = new ArrayList<>();
        try {
            String uri = getRestServiceUri() + UtilControllerResources.BREED_GET_ALL;
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            restClient.setCredentials( user.getUsername(), user.getPassword());
            LOG.info( "user=" + user);
            Breed[] breed = restClient.getForObject( uri, Breed[].class);
            if( breed != null) for (Breed br: breed) breedList.add( br);
        }
        catch( HttpClientErrorException e){
            processServerException(e, request, map);
        }
        return breedList;
    }

}
