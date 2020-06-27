package org.aloboda.algo.mincut;

import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.aloboda.algo.mincut.MinimalCutFinder.Vertex;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.aloboda.algo.mincut.MinimalCutFinder.InitialVertexLabel.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MinimalCutFinderTest {

    @Test
    public void when_trying_to_find_minimal_cut_on_small_tree_return_1() {
        // Given...
        final Set<Vertex> vertices = HashSet.of(
                Vertex.of(of("1"), of("2")),
                Vertex.of(of("2"), of("1"), of("3")),
                Vertex.of(of("3"), of("2"), of("4")),
                Vertex.of(of("4"), of("3"))
        );
        // When...
        final int minimalCut = new MinimalCutFinder().findMinimalCut(vertices.toJavaSet(), 1);
        // Then...
        assertThat(minimalCut, is(1));
    }

    @Test
    public void when_trying_to_find_minimal_cut_on_small_dense_graph_then_return_correct_result() {
        // Given...
        /*
         * A----B
         * |   /|
         * |  / |
         * | /  |
         * C----D
         * */
        final Set<Vertex> vertices = HashSet.of(
                Vertex.of(of("A"), of("B"), of("C")),
                Vertex.of(of("B"), of("A"), of("C"), of("D")),
                Vertex.of(of("C"), of("A"), of("B"), of("D")),
                Vertex.of(of("D"), of("C"), of("B"))
        );
        // When...
        final int minimalCut = new MinimalCutFinder().findMinimalCut(vertices.toJavaSet(), vertices.size() * vertices.size());
        // Then...
        assertThat(minimalCut, is(2));
    }

    @Test
    public void when_find_minimal_cut_on_large_set_then_return_correct_result() {
        // Given...
        final java.util.Set<Vertex> vertices = readFromResource("minimal_cut/large_sample.txt");
        // When...
        final int minimalCut = new MinimalCutFinder().findMinimalCut(vertices, vertices.size() * vertices.size());
        // Then...
        assertThat(minimalCut, is(17));

    }

    @SuppressWarnings("UnstableApiUsage")
    private static java.util.Set<Vertex> readFromResource(
            @SuppressWarnings("SameParameterValue") final String resourcePath
    ) {
        try {
            final CharSource charSource = Resources.asCharSource(Resources.getResource(resourcePath), StandardCharsets.UTF_8);
            try (final Stream<String> lines = charSource.lines()) {
                return lines.map(MinimalCutFinderTest::asVertex).collect(Collectors.toSet());
            }
        } catch (final IOException e) {
            throw new IllegalStateException(String.format("Unable to read from resource: %s", resourcePath));
        }
    }

    private static Vertex asVertex(final String serializedVertex) {
        final List<MinimalCutFinder.InitialVertexLabel> labels = StreamSupport.stream(Splitter.on("\t").omitEmptyStrings().split(serializedVertex).spliterator(), false
        ).map(MinimalCutFinder.InitialVertexLabel::of).collect(Collectors.toList());

        return Vertex.of(labels.get(0), labels.subList(1, labels.size()));
    }
}
