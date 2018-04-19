package com.github.datalking.common;

/**
 * 标记顺序的接口
 * <p>
 * 数值越小，优先级越高
 *
 * @author yaoo on 4/18/18
 */
public interface Ordered {

    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int getOrder();

}
