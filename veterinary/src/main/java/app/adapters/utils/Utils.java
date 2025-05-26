package app.adapters.utils;

import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Scanner;

public abstract class Utils {
    @Getter
    private static Scanner reader = new Scanner(System.in);
    @Getter
    private static final Timestamp currentDate = new Timestamp(System.currentTimeMillis());
}
