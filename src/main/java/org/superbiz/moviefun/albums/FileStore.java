package org.superbiz.moviefun.albums;

import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


public class FileStore implements BlobStore {

    private final Tika tika = new Tika();

    @Override
    public void put(Blob blob) throws IOException {
       File file = new File("covers/"+blob.getName());
        file.delete();
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(blob.getInputStream(), outputStream);
        }
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        File file = new File("covers/"+name);
        if(!file.exists()){
            return Optional.empty();
        }
        return Optional.of(new Blob(name, new FileInputStream(file), tika.detect(file)));
    }

    @Override
    public void deleteAll() {
           try {
            Files.walk(Paths.get("covers"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
