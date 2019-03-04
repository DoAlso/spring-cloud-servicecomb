package com.sample.scca.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface ProjectRepo extends JpaRepository<Project, Long> {

    Project findByName(String name);

}
