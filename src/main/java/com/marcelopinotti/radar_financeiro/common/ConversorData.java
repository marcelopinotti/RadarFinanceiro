package com.marcelopinotti.radar_financeiro.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversorData {
    public static String converterData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(data);
    }
}
