package ru.reeson2003.amuletproxy.handling;

public interface RequestProcessor<P, R> {

    Response<R> process(Request<P> request);
}
