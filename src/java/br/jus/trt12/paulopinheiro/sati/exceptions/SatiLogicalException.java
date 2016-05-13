package br.jus.trt12.paulopinheiro.sati.exceptions;

public class SatiLogicalException extends Exception {
    public SatiLogicalException() {}
    public SatiLogicalException(String msg) {super(msg);}
    public SatiLogicalException(String msg, Exception ex) {super(msg,ex);}
}
