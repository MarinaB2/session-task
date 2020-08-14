package com.sessiontask.demo.services;

import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    DataSource dataSource;

    @Autowired
    NoticeRepository entryRepository;

    public NoticeService(){

    }
        public Notice getNotice(int id) {
            return entryRepository.findById(id).get();
        }

        public List<Notice> getAllNotices() {
            return entryRepository.findAll(Sort.by(Sort.Direction.DESC, "published"));
        }

        public void deleteNotice(int id) {
            entryRepository.deleteById(id);
        }

        public void addNotice(Notice notice) {
            entryRepository.save(notice);
        }

        public void updateNotice(Notice notice) {
            entryRepository.save(notice);
        }





}
