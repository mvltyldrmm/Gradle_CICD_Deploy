/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package java_ci_cd;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
    public String getGreeting() {   //TEST
        return "Hello world.";
    }

    public static boolean search(ArrayList<Integer> array, int e)
    {
        System.out.println("Inside Search");  //TEST
        if(array==null) return false;
        for(int e1t:array){
            if(e1t==e){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hi!");  //TEST GET ISTEGI
        post("/compute", (req, res) -> {   //POST ISTEGI

        String input4 = req.queryParams("input4");
        // BOSLUKLU STRING GIRDISI. QUIZLER ICIN
        String input2 = req.queryParams("input2").replaceAll("\\s","");
        //VIZE 1
        String input3 = req.queryParams("input3").replaceAll("\\s","");
        //VIZE 2
        String input1 = req.queryParams("input1").replaceAll("\\s","");
        //OGRENCI NUMARASI
        Double result = App.input_controls(input4, input1, input2, input3);

        Map<String, Double> map = new HashMap<String, Double>();
        map.put("result", result);  //RESULTUN GET ILE GONDERILECEK KISIM
        return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
        get("/compute",
        (rq, rs) -> {
        Map<String, String> map = new HashMap<String, String>();
        map.put("result", "Hesaplama yapılmadı.!");  //HATALI HESAPLAMA DURUMU
        return new ModelAndView(map, "compute.mustache");
        },
        new MustacheTemplateEngine());  //TEMPLATE'IN CEKILMESI
        }

        static int getHerokuAssignedPort() {  //PORT AYARLANMASI
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
            }
            return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
        }

        public static Double input_controls(String input4, String input1, String input2, String input3){  //INPUTLARIN ISLEME ALINDIGI KISIM

        if(input4 == "" || input1 == "" || input2 == "" || input3 == ""){
            return 0.0;  //INPUTLARIN BOS OLMASI DURUMUNDA 0 DONDURULECEK
        }

        if(input1.length()!=9){  //OGRENCI NUMARASI 9 HANELI OLMALI ; 180303024
            return 0.0;
        }

        int input2AsInt = Integer.parseInt(input2);
        int input3AsInt = Integer.parseInt(input3);  //INTEGER DONUSUMU

        java.util.Scanner sc1 = new java.util.Scanner(input4);   //QUIZLERIN BOSLUKLARA GORE AYRILMASI
        sc1.useDelimiter("[;\r\n]+");
        java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
        while (sc1.hasNext())
        {
        int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
        inputList.add(value);
        }
        sc1.close();

        if (inputList.size()<=1){  //EN AZ 2 QUIZ GIRILMESI GEREK
            return 0.0;
        }

        int sum = 0;
        
        for(int num : inputList){
            sum = sum+num;  //QUIZLERIN ORTALAMASININ ALINMASI
        }

        Double result = input2AsInt*0.20 + input3AsInt*0.20 + (sum/inputList.size())*0.60;  //QUIZLER %60 ETKILERKEN VIZELERIN HER BIRI %20 ETKILEMEKTE

        return result;
    }
}