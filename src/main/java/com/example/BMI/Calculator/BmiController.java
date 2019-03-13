package com.example.BMI.Calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;

@Controller
public class BmiController {

    private double weight;
    private double height;

    @GetMapping("/bmicalculate")
    public void getBmiIndex(@RequestParam double height, @RequestParam double weight, @RequestParam int age, @RequestParam String gender,
                            @RequestParam String activity, ModelMap map, DecimalFormat df) {

        df = new DecimalFormat("#.##");

        getDescription(height, weight, map, df);

        //-----------------------------------------------
        getPerfectWeight(height, map, df);

        double bmr = getBasicBmr(height, weight, age, gender);

        getBmr(activity, map, df, bmr);

    }

    private void getBmr(@RequestParam String activity, ModelMap map, DecimalFormat df, double bmr) {
        double bmr2;
        String bmrResult = "";
        if (activity.equals("lazy")) {
            bmr = bmr * 1.2;
            bmrResult = "Twoje zapotrzebowanie kaloryczne wynosi: " + df.format(bmr) + " kalorii";
        }
        if (activity.equals("low")) {
            bmr2 = bmr * 1.4;
            bmr = bmr * 1.3;
            bmrResult = "Twoje zapotrzebowanie kaloryczne wynosi: " + df.format(bmr) + "-" + df.format(bmr2) + " kalorii";
        }
        if (activity.equals("regular")) {
            bmr2 = bmr * 1.6;
            bmr = bmr * 1.5;
            bmrResult = "Twoje zapotrzebowanie kaloryczne wynosi: " + df.format(bmr) + "-" + df.format(bmr2) + " kalorii";
        }
        if (activity.equals("sport")) {
            bmr2 = bmr * 1.8;
            bmr = bmr * 1.7;
            bmrResult = "Twoje zapotrzebowanie kaloryczne wynosi: " + df.format(bmr) + "-" + df.format(bmr2) + " kalorii";
        }
        if (activity.equals("pro")) {
            bmr2 = bmr * 2.2;
            bmr = bmr * 1.9;
            bmrResult = "Twoje zapotrzebowanie kaloryczne wynosi: " + df.format(bmr) + "-" + df.format(bmr2) + " kalorii";
        }

        map.put("bmr", bmrResult);
    }

    private double getBasicBmr(@RequestParam double height, @RequestParam double weight, @RequestParam int age, @RequestParam String gender) {
        double bmr = 9.99 * weight + ((6.25 * height) - (4.92 * age));

        if (gender.equals("male")) {
             bmr += 5; }
            //if (gender.equals("female")) {
            else {
                bmr -=  161;
            }
        return bmr;
    }

    private void getPerfectWeight(@RequestParam double height, ModelMap map, DecimalFormat df) {
        //Idealna waga: przedział liczony na podstawie wzoru: waga*bmi=18.5 - waga*bmi=25
        String perfectWeight = "Idealna waga dla Twojego wzrostu (" + height + " cm) wynosi: " + df.format(0.0001 * height * height * 18.5) + "kg - " + df.format(0.0001 * height * height * 25) + "kg";
        map.put("perfectWeight", perfectWeight);
    }

    private void getDescription(@RequestParam double height, @RequestParam double weight, ModelMap map, DecimalFormat df) {
        double bmi;
        map.put("result", bmi = weight / (0.0001 * height * height));

        String description = "";

        if (bmi < 16) {
            description = "Jesteś wygłodzony! Twój indeks BMI wynosi jedynie: " + df.format(bmi);
        }
        if (bmi < 17 && bmi >= 16) {
            description = "Jesteś wychudzony! Twój indeks BMI wynosi jedynie: " + df.format(bmi);
        }
        if (bmi < 18.5 && bmi >= 17) {
            description = "Zacznij jeść, jesteś niedożywiony! Twój indeks BMI wynosi jedynie: " + df.format(bmi);
        }
        if (bmi >= 18.5 && bmi < 25) {
            description = "Twoja waga jest w normie, a Twój indeks BMI wynosi: " + df.format(bmi);
        }
        if (bmi < 30 && bmi >= 25) {
            description = "Masz delikatną nadwagę. Twój indeks BMI wynosi: " + df.format(bmi);
        }
        if (bmi < 35 && bmi >= 30) {
            description = "Masz otyłość pierwszego stopnia! Twój indeks BMI wynosi: " + df.format(bmi);
        }
        if (bmi < 40 && bmi >= 35) {
            description = "Masz otyłość drugiego stopnia! Twój indeks BMI wynosi: " + df.format(bmi);
        }
        if (bmi >= 40) {
            description = "Masz ogromną nadwagę, zrób coś z tym! Twój indeks BMI wynosi: " + df.format(bmi);
        }
        map.put("description", description);
    }

    @GetMapping("/home")
    public String hello(ModelMap map) {
        map.put("message", " Witaj na stronie obliczającej Twój indeks BMI");
        return "home";
    }

}


