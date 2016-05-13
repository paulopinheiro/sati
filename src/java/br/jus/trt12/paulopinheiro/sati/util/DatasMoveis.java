package br.jus.trt12.paulopinheiro.sati.util;

import java.util.Calendar;
import java.util.Date;

public class DatasMoveis {
    private static int x;
    private static int y;
    
    public static Date pascoa(int ano) {
        return calculoPascoa(ano);
    }
    public static Date corpusChristi(int ano) {
        return Util.somaData(pascoa(ano),60);
    }

    public static Date carnaval(int ano) {
        return Util.somaData(pascoa(ano),-47);
    }

    private static Date calculoPascoa(int ano) {
        int dia;
        int mes;
        Date pascoa = null;

        buscaFaixa(ano);

        int a = ano%19;
        int b = ano%4;
        int c = ano%7;
        int z = ((19*a)+x);
        int d = z%30;
        int v =((2*b)+(4*c)+(6*d)+y);
        int e = v%7;

        int fator = d+e;

        if (fator<10) {
            dia=fator+22;
            mes=Calendar.MARCH;
        } else {
            dia=fator-9;
            mes=Calendar.APRIL;
        }

        if (mes==Calendar.APRIL) {
            if (dia==26) dia=19;
            if ((dia==25)&&(d==28)&&(a>10)) dia=18;
        }

        pascoa = Util.dataPura(ano,mes,dia);

        return pascoa;
    }

    private static void buscaFaixa(int ano) {
        x=0;
        y=0;
        if (ano >=1582) {
            if (ano <=1699) {
                x=22; y=2;
            } else {
                if (ano <=1799) {
                    x=23; y=3;
                } else {
                    if (ano <= 1899) {
                        x=24; y=4;
                    } else {
                        if (ano <= 2099) {
                            x=24; y=5;
                        } else {
                            if (ano <=2199) {
                                x=24; y=6;
                            } else {
                                if (ano <= 2299) {
                                    x=25; y=7;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
