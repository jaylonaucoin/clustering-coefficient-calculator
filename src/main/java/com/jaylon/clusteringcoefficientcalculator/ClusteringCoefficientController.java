package com.jaylon.clusteringcoefficientcalculator;

import com.jaylon.clusteringcoefficientcalculator.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ClusteringCoefficientController {

    @PostMapping("/calculate")
    public String calculateCoefficient(@RequestParam("neighbors") int neighbors,
                                       @RequestParam("links") int links,
                                       Model model) {

        // Validation and error handling
        if (neighbors < 0 || links < 0) {
            throw new ValidationException("Number of neighbors and links must be non-negative.");
        }
        if (neighbors == 0) {
            throw new ValidationException("Number of neighbors cannot be zero.");
        }
        if (links > (neighbors * (neighbors - 1)) / 2) {
            if (neighbors == 1) {
                throw new ValidationException("Number of links exceeds the maximum possible for " + neighbors + " neighbor.");
            }
            throw new ValidationException("Number of links exceeds the maximum possible for " + neighbors + " neighbors.");
        }
        if (links == 0) {
            return "0";
        }

        // Calculate clustering coefficient
        String coefficient = calculateClusteringCoefficient(neighbors, links);

        // Add the coefficient to the model to display it in the result page
        model.addAttribute("coefficient", coefficient);

        // Return the name of the Thymeleaf template for the result page
        return "result";
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ValidationException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        // Return to the form with the error message
        return "form";
    }

    // Method to calculate the clustering coefficient
    private String calculateClusteringCoefficient(int neighbors, int links) {
        int numerator = 2 * links;
        int k2 = neighbors - 1;
        int denominator = neighbors * k2;
        double coefficient = (double) numerator / denominator;

        if (coefficient != 0) {
            int[] fractionArr = reduceFraction(numerator, denominator);
            String fraction = fractionArr[0] + "/" + fractionArr[1];
            return fraction;
        } else {
            return "0";
        }
    }

    // Function to reduce a fraction to its lowest form
    static int[] reduceFraction(int x, int y) {
        int d = __gcd(x, y);
        x = x / d;
        y = y / d;
        return new int[] {x, y};
    }

    static int __gcd(int a, int b) {
        if (b == 0)
            return a;
        return __gcd(b, a % b);
    }
}
