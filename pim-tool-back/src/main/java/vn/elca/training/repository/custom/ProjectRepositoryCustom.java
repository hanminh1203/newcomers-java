package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> findAll();
    List<Project> findProjectByName(String keyWord);
    Project findOneProjectByName(String keyWord);

    long countByNumber(int projectNumber);

    void deleteAllByIds(List<Long> ids);
}
