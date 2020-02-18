package com.project.search.adapter.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class FileDataLoad implements IDataLoadAdapterBase {
  private static final Logger logger = LoggerFactory.getLogger(FileDataLoad.class);
  private static final String UTF_8 = "UTF-8";

  private ResourceLoader resourceLoader;

  @Autowired
  public FileDataLoad(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  // public FileDataLoad() {}

  @Override
  public String load(String entity) throws IOException {

    InputStream inputStream =
        resourceLoader.getResource("classpath:data/" + entity + ".json").getInputStream();

    Reader reader = new InputStreamReader(inputStream, UTF_8);

    return FileCopyUtils.copyToString(reader);
  }
}
