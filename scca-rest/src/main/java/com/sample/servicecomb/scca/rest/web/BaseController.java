package com.sample.servicecomb.scca.rest.web;

import com.sample.scca.SccaCoreProperties;
import com.sample.scca.domain.*;
import com.sample.scca.service.BaseOptService;
import com.sample.scca.service.PersistenceService;
import com.sample.scca.service.PropertiesService;
import com.sample.scca.service.UrlMakerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/27.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class BaseController {

    @Autowired
    protected SccaCoreProperties sccaProperties;

    @Autowired
    protected EnvRepo envRepo;
    @Autowired
    protected EnvParamRepo envParamRepo;
    @Autowired
    protected EncryptKeyRepo encryptKeyRepo;
    @Autowired
    protected ProjectRepo projectRepo;
    @Autowired
    protected LabelRepo labelRepo;

    @Autowired
    protected BaseOptService baseOptService;
    @Autowired
    protected PersistenceService persistenceService;
    @Autowired
    protected PropertiesService propertiesService;
    @Autowired
    protected UrlMakerService urlMakerService;

}
