package com.github.datalking.common;

/**
 * 标记顺序的接口
 *
 * @author yaoo on 4/18/18
 */
public interface Ordered {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int getOrder();

}
