package vn.elca.training.repository.custom;

import vn.elca.training.model.ProjectStatus;
import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> findAll();
    List<Project> findProjectByKeyword(String keyword);
    List<Project> findProjectByStatus(Enum<ProjectStatus> status);
    List<Project> findProjectByKeywordAndStatus(String keyword, Enum<ProjectStatus> status);

    long countByNumber(int projectNumber);

    void deleteAllByIds(List<Long> ids);
}
