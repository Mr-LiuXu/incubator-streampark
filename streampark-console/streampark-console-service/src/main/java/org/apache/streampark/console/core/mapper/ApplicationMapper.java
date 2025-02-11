/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.streampark.console.core.mapper;

import org.apache.streampark.console.core.entity.Application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationMapper extends BaseMapper<Application> {

    IPage<Application> page(Page<Application> page, @Param("application") Application application);

    Application getApp(@Param("application") Application application);

    void updateTracking(@Param("application") Application application);

    List<Application> getByTeamId(@Param("teamId") Long teamId);

    boolean mapping(@Param("application") Application appParam);

    List<String> getRecentK8sNamespace(@Param("limitSize") int limit);

    List<String> getRecentK8sClusterId(@Param("executionMode") int executionMode, @Param("limitSize") int limit);

    List<String> getRecentFlinkBaseImage(@Param("limitSize") int limit);

    List<String> getRecentK8sPodTemplate(@Param("limitSize") int limit);

    List<String> getRecentK8sJmPodTemplate(@Param("limitSize") int limit);

    List<String> getRecentK8sTmPodTemplate(@Param("limitSize") int limit);

    void resetOptionState();

    Boolean existsByTeamId(@Param("teamId") Long teamId);

    Boolean existsByJobName(@Param("jobName") String jobName);

    List<Application> getByProjectId(@Param("projectId") Long id);
}
