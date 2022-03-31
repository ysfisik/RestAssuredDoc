package testexample;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class reqResApi {

    @Test()
    public void getSingleUser() {

        RestAssured.baseURI = "https://reqres.in";//base url olusturduk
        RequestSpecification request = RestAssured.given();//RequestSpecification sınıfında request objesi olusturduk
        //Response sınıfında response objesi olusturduk yapılan istek get olduğunu burda bilirtiyoruz.yolumuzu url kısmına yazıyoruz
        Response response = request.get("/api/users/2");
        System.out.println(response.getStatusCode());//status code ekrana yazdırma
        System.out.println(response.getBody().asString());//gelen cevabın body kısmını ekrana string olarak yazdık
        Assert.assertEquals(response.getStatusCode(), 200);//test yapıldı statüs cod 200 e esitmi?
        //jsonbody içindeki bilgileri get ile alıp alıyorum.
        //get kısmının içine hangi bilgiye ulaşmak istiyorsam onu yazıyorum.
        //en sonda bilgiyi tostring le stringe çeviriyoruz
        JsonPath jsonBody = response.jsonPath();//JSONPath  JSON verileri arasında bize sorgu yapma fırsatının verir
        System.out.println(jsonBody.get("data.id").toString());
        System.out.println(jsonBody.toString());//!!!!!!!!!!! neden tüm json bady ekrana yazdıramıyorum sor????????
        System.out.println(response.then().log().all());
    }

    @Test()
    public void getListUser() {

        RestAssured.baseURI = "https://reqres.in";//base url olusturduk
        RequestSpecification request = RestAssured.given();//RequestSpecification sınıfında request objesi olusturduk
        //Response sınıfında response objesi olusturduk yapılan istek get olduğunu burda bilirtiyoruz.yolumuzu url kısmına yazıyoruz
        Response response = request.get("/api/users?page=2");
        System.out.println(response.getStatusCode());//statüs cod ekrana yazdırma
        JsonPath jsonBody = response.jsonPath();//JSONPath  JSON verileri arasında bize sorgu yapma fırsatının verir
        System.out.println(response.getBody().asString());//gelen cevabın body kısmını ekrana string olarak yazdık
        //jsonbody içindeki bilgileri get ile alıp alıyorum.
        //get kısmının içine hangi bilgiye ulaşmak istiyorsam onu yazıyorum.
        //en sonda bilgiyi tostring le stringe çeviriyoruz
        System.out.println(jsonBody.get("data[0].id").toString());
        Assert.assertEquals(response.getStatusCode(), 200);//test yapıldı statüs cod 200 e esitmi?
        System.out.println(jsonBody.toString());//!!!!!!!!!!! neden tüm json bady ekrana yazdıramıyorum sor????????



    }

    @Test()
    public void postUserYöntem1() {

        JSONObject request = new JSONObject();//json objesi olusturur

        request.put("name", "yusuf");//göndereceğimiz değerleri request in içine atıyor.
        request.put("Job", "enginier");//göndereceğimiz değerleri request in içine atıyor.

        given().//başlangıç koşulları given. dan sonra yazılır
                header("Content-type", "application/json").//gelen içeriğin json formatında olması gerektiğini belirtiyo.
                contentType(ContentType.JSON).//Gelen response içeriği tipinin json olsuğunu kontrol eder.
                body(request.toJSONString()).//body deki requesti jsonstringe çeviriyor
                when().
                post("https://reqres.in/api/users").//istek yapılan kısımdır
                then().//kod çalıştıktan sonra test etmek istediğimiz işleri then. dan sonra yazarız
                statusCode(201).
                log().body();//gelen cevabın body kısmını ekrana yazdırır.


    }

    @Test()
    public void postUserYöntem2() {

        RestAssured.baseURI = "https://reqres.in";//base url olusturduk
        RequestSpecification request = given();//RequestSpecification sınıfında request objesi olusturduk
        JSONObject requestParams = new JSONObject();//ismi requestParams olan bir json objesi olusturduk
        requestParams.put("name", "veysi");//body kısmı için key volue şeklinde bilgilerimi put ile json objesine ekliyorum
        requestParams.put("job", "pc");//body kısmı için key volue şeklinde bilgilerimi put ile json objesine ekliyorum
        request.body(requestParams.toJSONString());//request(istek) değişkenimin body sine oluşturduğum json ları atıyorum.
        request.header("Content-Type", "application/json"); // header kısmında kısıtlama yaparız gelen değerin json olması gerekir.
        //Response sınıfında response objesi olusturduk yapılan istek get olduğunu burda bilirtiyoruz.yolumuzu url kısmına yazıyoruz
        Response response = request.post("/api/users");
        ResponseBody body = response.getBody();//gelen response un body kısmını body diye bir değişkene atadım.
        System.out.println(body.asString());//body kısmını string olarak ekrana yazdırdık
        JsonPath jsonBody = response.jsonPath();//JSONPath  JSON verileri arasında bize sorgu yapma fırsatının verir
        String isim = jsonBody.get("name");//name değerini isim değişkenine atadık
        System.out.println(isim);//isim değişkenini ekrana yazdırdık
        Assert.assertEquals(response.getStatusCode(), 201);//test yapıldı statüs cod 201 e esitmi?


    }

    @Test()
    public void putUpdate() {


        RestAssured.baseURI = "https://reqres.in";//base url olusturduk
        RequestSpecification request = given();//RequestSpecification sınıfında request objesi olusturduk
        JSONObject requestParams = new JSONObject();//ismi requestParams olan bir json objesi olusturduk
        requestParams.put("name", "mesut");//body kısmı için key volue şeklinde bilgilerimi put ile json objesine ekliyorum
        requestParams.put("job", "pc");//body kısmı için key volue şeklinde bilgilerimi put ile json objesine ekliyorum
        request.body(requestParams.toJSONString());//request(istek) değişkenimin body sine oluşturduğum json ları atıyorum.
        request.header("Content-Type", "application/json"); // header kısmında kısıtlama yaparız gelen değerin json olması gerekir.
        //Response sınıfında response objesi olusturduk yapılan istek get olduğunu burda bilirtiyoruz.yolumuzu url kısmına yazıyoruz
        Response response = request.put("/api/users/2");
        ResponseBody body = response.getBody();//gelen response un body kısmını body diye bir değişkene atadım.
        System.out.println(body.asString());//body kısmını string olarak ekrana yazdırdık
        JsonPath jsonBody = response.jsonPath();//JSONPath  JSON verileri arasında bize sorgu yapma fırsatının verir
        String isim = jsonBody.get("name");//name değerini isim değişkenine atadık
        System.out.println(isim);//isim değişkenini ekrana yazdırdık
        System.out.println(response.then().log().body());//body kısmını ekrana yazdırma
        Assert.assertEquals(response.getStatusCode(), 200);//test yapıldı statüs cod 200 e esitmi?


    }

    @Test()
    public void patchUpdate() {


        RestAssured.baseURI = "https://reqres.in";//base url olusturduk
        RequestSpecification request = given();//RequestSpecification sınıfında request objesi olusturduk
        JSONObject requestParams = new JSONObject();//ismi requestParams olan bir json objesi olusturduk
        requestParams.put("name", "yakup");//body kısmı için key volue şeklinde bilgilerimi put ile json objesine ekliyorum
        request.body(requestParams.toJSONString());//request(istek) değişkenimin body sine oluşturduğum json ları atıyorum.
        request.header("Content-Type", "application/json"); // header kısmında kısıtlama yaparız gelen değerin json olması gerekir.
        //Response sınıfında response objesi olusturduk yapılan istek get olduğunu burda bilirtiyoruz.yolumuzu url kısmına yazıyoruz
        Response response = request.patch("/api/users/2");
        ResponseBody body = response.getBody();//gelen response un body kısmını body diye bir değişkene atadım.
        System.out.println(body.asString());//body kısmını string olarak ekrana yazdırdık
        JsonPath jsonBody = response.jsonPath();//JSONPath  JSON verileri arasında bize sorgu yapma fırsatının verir
        String isim = jsonBody.get("name");//name değerini isim değişkenine atadık
        System.out.println(isim);//isim değişkenini ekrana yazdırdık
        System.out.println(response.then().log().body());//body kısmını ekrana yazdırma
        Assert.assertEquals(response.getStatusCode(), 200);//test yapıldı statüs cod 200 e esitmi?



    }
    @Test()
    public void deleteUser() {

        RestAssured.baseURI = "https://reqres.in";//base url olusturduk
        RequestSpecification request = RestAssured.given();//RequestSpecification sınıfında request objesi olusturduk
        //Response sınıfında response objesi olusturduk yapılan istek delete olduğunu burda bilirtiyoruz.yolumuzu url kısmına yazıyoruz
        Response response = request.delete("/api/users/2");
        System.out.println(response.getStatusCode());//statüs cod ekrana yazdırma
        Assert.assertEquals(response.getStatusCode(), 204);//test yapıldı statüs cod 200 e esitmi?

    }
}
