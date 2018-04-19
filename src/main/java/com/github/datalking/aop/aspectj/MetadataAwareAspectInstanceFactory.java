package com.github.datalking.aop.aspectj;

/**
 * @author yaoo on 4/19/18
 */
public interface MetadataAwareAspectInstanceFactory {


    AspectMetadata getAspectMetadata();

    //Object getAspectCreationMutex();

}
