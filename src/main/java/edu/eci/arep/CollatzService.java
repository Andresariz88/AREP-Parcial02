package edu.eci.arep;

import spark.Spark;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class CollatzService {
    public static void main(String... args){
        port(getPort());
        staticFileLocation("/");
        get("collatzsequence", (req,res) -> {
            res.type("application/json");
            return makeResponse(Integer.valueOf(req.queryParams("value")));
        });
    }

    private static String makeResponse(Integer value) {
        String res = "{\n" +
                "\n" +
                " \"operation\": \"collatzsequence\",\n" +
                "\n" +
                " \"input\":" + value + ",\n" +
                "\n" +
                " \"output\": \"";
        List<Integer> collatz = collatz(value);
        for (int i = 0; i < collatz.size(); i++) {
            if (i == collatz.size()-1) {
                res += collatz.get(i);
            } else {
                res += collatz.get(i) + " -> ";
            }
        }
        res += "\"\n"+
                "\n" +
                "}";
        System.out.println(res);
        return res;
    }

    private static List<Integer> collatz(Integer value) {
        List<Integer> res = new ArrayList<>();
        res.add(value);
        int i = 1;
        while (res.get(i-1) != 1) {
            if (res.get(i-1) % 2 == 0) {
                res.add(res.get(i-1)/2);
            } else {
                res.add(3 * res.get(i-1) + 1);
            }
            i++;
        }
        return res;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
