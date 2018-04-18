package com.github.datalking.aop.framework;

import java.io.Serializable;

/**
 * @author yaoo on 4/18/18
 */
public class ProxyConfig implements Serializable {

    private boolean proxyTargetClass = false;

//    boolean exposeProxy = false;
//    private boolean frozen = false;
//    private boolean optimize = false;


    public ProxyConfig() {
    }

    public ProxyConfig(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    @Override
    public String toString() {
        return "ProxyConfig{" +
                "proxyTargetClass=" + proxyTargetClass +
                '}';
    }

}
