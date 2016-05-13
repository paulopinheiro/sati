package br.jus.trt12.paulopinheiro.sati.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Util {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static boolean vazio(String s) {
        boolean resposta = true;
        if (!(s==null)) if (!s.trim().isEmpty()) resposta=false;
        return resposta;
    }
    public static String limpaException(String s) {
        return s.replace("Internal Exception: org.postgresql.util.PSQLException:", "");
    }
//
//    public static Boolean maiorQue(Date data1, Date data2) {
//        Boolean resposta = null;
//        try {
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
//            String strData1 = sdf1.format(data1);
//            String strData2 = sdf1.format(data2);
//            resposta = Integer.parseInt(strData1) > Integer.parseInt(strData2);
//        } catch (Exception ex) {}
//        return resposta;
//    }

    public static Date dataPura(int ano, int mes, int dia) {
        if ((ano==0)||(mes==0)||(dia==0)) return null;
        Date resposta = null;
        DecimalFormat df2 = new DecimalFormat("00");
        DecimalFormat df4 = new DecimalFormat("0000");
        String elemento = df2.format(dia) + "/" +
                          df2.format(mes) + "/" +
                          df4.format(ano);
        try {resposta = sdf.parse(elemento);} catch (ParseException ex) {}

        return resposta;
    }

    public static int conversaoCalendarMes(Integer mesCalendar) {
        if (mesCalendar==null) mesCalendar=-1;
        switch (mesCalendar) {
            case Calendar.JANUARY: return 1;
            case Calendar.FEBRUARY: return 2;
            case Calendar.MARCH: return 3;
            case Calendar.APRIL: return 4;
            case Calendar.MAY: return 5;
            case Calendar.JUNE: return 6;
            case Calendar.JULY: return 7;
            case Calendar.AUGUST: return 8;
            case Calendar.SEPTEMBER: return 9;
            case Calendar.OCTOBER: return 10;
            case Calendar.NOVEMBER: return 11;
            case Calendar.DECEMBER: return 12;
            default: return 0;
        }
    }

    public static int conversaoMesCalendar(Integer mes) {
        if (mes==null) mes=-1;

        if (mes==null) mes=-1;
        switch (mes) {
            case 1: return Calendar.JANUARY;
            case 2: return Calendar.FEBRUARY;
            case 3: return Calendar.MARCH;
            case 4: return Calendar.APRIL;
            case 5: return Calendar.MAY;
            case 6: return Calendar.JUNE;
            case 7: return Calendar.JULY;
            case 8: return Calendar.AUGUST;
            case 9: return Calendar.SEPTEMBER;
            case 10: return Calendar.OCTOBER;
            case 11: return Calendar.NOVEMBER;
            case 12: return Calendar.DECEMBER;
            default: return 0;
        }
    }

    public static String dataString(Date date) {
        if (date==null) return null;
        return sdf.format(date);
    }

    public static Date somaData(Date dataDada,int dias) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dataDada);

        calendar.add(Calendar.DATE, dias);

        return calendar.getTime();
    }

    public static boolean diaMesValido(int dia, int mes) {
        if (mes==2)
            if (dia>28) return false;   //feriados em 29/02 não serão aceitos

        if ((dia < 1)||(dia > ultimoDiaMes(mes,anoAtual()))) return false;

        return true;
    }

    public static int anoAtual() {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(new Date());
        return calendario.get(Calendar.YEAR);
    }

    public static int ultimoDiaMes(int mes, int ano) {
        Calendar calendario = new GregorianCalendar();
        calendario.set(Calendar.YEAR, ano);
        calendario.set(Calendar.MONTH,conversaoMesCalendar(mes));

        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static boolean isIntervaloDatasValido(Date desde, Date ate) {
        boolean resposta = true;
        if (!(desde==null)&&!(ate==null))
            if (desde.after(ate)) resposta = false;
        return resposta;
    }

    public static boolean coincidenciaDatas(Date desde1, Date ate1, Date desde2, Date ate2) {
        boolean resposta = true;

        if (desde1==null) {
            if (!(ate1==null) && !(desde2==null)) {
                if (desde2.after(ate1)) resposta=false;
            }
        } else {    //desde1 não é nulo
            if (desde2==null) {
                if (!(ate2==null)) {
                    if (desde1.after(ate2)) resposta=false;
                }
            } else {    //desde1 não é nulo, desde2 não é nulo
                if (ate1==null) {
                    if (!(ate2==null)) {
                        if (desde1.after(ate2)) resposta = false;
                    }
                } else {    //desde1 não é nulo, desde2 não é nulo, ate1 não é nulo
                    if (desde2.after(ate1)) resposta = false;
                }
            }
        }

        return resposta;
    }
}
