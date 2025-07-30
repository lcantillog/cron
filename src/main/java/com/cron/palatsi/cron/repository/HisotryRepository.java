package com.cron.palatsi.cron.repository;

import com.cron.palatsi.cron.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HisotryRepository extends JpaRepository<History,Long> {
}
