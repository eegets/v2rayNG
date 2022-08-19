
package com.forest.bss.sdk;

//
// An observer of an Observable.
//
public interface Observer<E,M,A>
{
    void notify(E observable, M msg, A arg);
}
