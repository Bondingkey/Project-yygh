package com.gzc.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gzc.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author gzc
 * @since 2023-12-02
 */
public interface DictService extends IService<Dict> {

    void download(HttpServletResponse response) throws IOException;

    void upload(MultipartFile file) throws IOException;

    List getChildListByPid(Long pid);

    String getNameByValue(Long value);

    String getNameByValueAndCode(String code, Long value);

}
