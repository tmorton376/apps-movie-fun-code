package org.superbiz.moviefun.movies;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;
import org.superbiz.moviefun.CsvUtils;

import java.util.List;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Component
public class MovieFixtures {

    private final ObjectReader objectReader;
    private final CsvUtils csvUtils = new CsvUtils();

    public MovieFixtures() {
        CsvSchema schema = CsvSchema.builder()
            .addColumn("title")
            .addColumn("director")
            .addColumn("genre")
            .addColumn("rating", NUMBER)
            .addColumn("year", NUMBER)
            .build();

        objectReader = new CsvMapper().readerFor(Movie.class).with(schema);
    }

    public List<Movie> load() {
        return csvUtils.readFromCsv(objectReader, "movie-fixtures.csv");
    }
}
