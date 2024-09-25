package com.queueup.qup.service;

import com.queueup.qup.repository.TokenRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AverageTokenProcessTimeCalculatorService {

    private final TokenRepo tokenRepo;

    public AverageTokenProcessTimeCalculatorService(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public String getAverageTokenProcessTime() {
        List<Date> times = tokenRepo.getAllTokenCompletionTime().stream().filter(val -> !ObjectUtils.isEmpty(val)).collect(Collectors.toList());

        if (times.isEmpty()) {
            return "0.0 minute/s";
        }
        long totalDifferenceInMillis = 0;
        for (int i = 0; i < times.size(); i++) {
            if (i == 0) {
                long difference = Math.abs(new Date().getTime() - times.get(i).getTime());
                totalDifferenceInMillis += difference;
            } else {
                long difference = Math.abs(times.get(i).getTime() - times.get(i - 1).getTime());
                totalDifferenceInMillis += difference;
            }
        }

        double averageDifferenceInMillis = (double) totalDifferenceInMillis / (times.size());

        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(averageDifferenceInMillis / (1000 * 60)));
        return df.format(averageDifferenceInMillis / (1000 * 60)) + " minute/s";
    }
}
