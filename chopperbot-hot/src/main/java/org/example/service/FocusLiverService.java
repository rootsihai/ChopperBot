package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.bean.FocusLiver;

import java.util.List;

public interface FocusLiverService extends IService<FocusLiver> {

    List<FocusLiver> getFocusLivers();

    boolean deleteLivers(String room_id);

    boolean addLivers(FocusLiver liver);
}
