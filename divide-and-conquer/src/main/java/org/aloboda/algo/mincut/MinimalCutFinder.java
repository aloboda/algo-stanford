package org.aloboda.algo.mincut;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Represents Algorithm to find minimal cut in the provided Graph
 * Was introduced by Karger.
 * <p>
 * Examples of use:
 * - how to identify community
 * - how to identify the weakest spot in a chain
 */
public class MinimalCutFinder {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    int findMinimalCut(final Set<Vertex> vertices, final int attempts) {
        int bestAttemptMinCut = vertices.size() - 1;
        for (int i = 0; i < attempts; i++) {
            bestAttemptMinCut = Math.min(findMinimalCut(vertices), bestAttemptMinCut);
        }
        return bestAttemptMinCut;
    }

    private static int findMinimalCut(final Set<Vertex> vertices) {
        final Map<InitialVertexLabel, Vertex> initialNameToVertex = vertices.stream()
                .collect(Collectors.toMap(vertex -> vertex.getLabels().iterator().next(), Function.identity()));

        final Graph graph = Graph.builder().vertices(vertices.stream()
                .collect(Collectors.toMap(Vertex::getLabels, Function.identity()))).build();

        while (graph.vertices.size() > 2) {
            final Tuple2<Vertex, InitialVertexLabel> edge = graph.pickRandomEdge();
            final Vertex endVertex = initialNameToVertex.get(edge._2);
            final Vertex mergedVertex = graph.mergeVertices(edge._1, endVertex);
            mergedVertex.getLabels().forEach(label -> initialNameToVertex.put(label, mergedVertex));
        }
        final Iterator<Vertex> iterator = graph.vertices.values().iterator();
        final int firstVertexEdgesCount = iterator.next().getAdjacentVertices().size();
        final int secondVertexEdgesCount = iterator.next().getAdjacentVertices().size();
        if (firstVertexEdgesCount != secondVertexEdgesCount){
            throw new IllegalStateException("One node has less edges than the other");
        }
        return firstVertexEdgesCount;
    }

    @Value
    @Builder
    private static class Graph {
        private final Map<Set<InitialVertexLabel>, Vertex> vertices;

        private Tuple2<Vertex, InitialVertexLabel> pickRandomEdge() {
            final Vertex startVertex = this.vertices.get(selectItemRandomlyFromCollection(this.vertices.keySet()));
            final InitialVertexLabel endVertexLabel = selectItemRandomlyFromCollection(startVertex.getAdjacentVertices());
            return Tuple.of(startVertex, endVertexLabel);
        }

        private static <T> T selectItemRandomlyFromCollection(final Collection<T> items) {
            final ArrayList<T> list = new ArrayList<T>(items);
            return list.get(RANDOM.nextInt(0, items.size()));
        }


        private Vertex mergeVertices(final Vertex startVertex, final Vertex endVertex) {
            this.vertices.remove(startVertex.labels);
            this.vertices.remove(endVertex.labels);

            final Set<InitialVertexLabel> labels = io.vavr.collection.HashSet.ofAll(startVertex.labels).addAll(endVertex.labels).toJavaSet();
            final io.vavr.collection.List<InitialVertexLabel> startVertexEdges = io.vavr.collection.List.ofAll(startVertex.adjacentVertices).removeAll(labels);
            final io.vavr.collection.List<InitialVertexLabel> endVertexEdges = io.vavr.collection.List.ofAll(endVertex.adjacentVertices).removeAll(labels);
            final List<InitialVertexLabel> initialVertexLabels = io.vavr.collection.List.ofAll(startVertexEdges).appendAll(endVertexEdges).toJavaList();
            final Vertex mergedVertex = Vertex.of(
                    labels,
                    initialVertexLabels
            );
            this.vertices.put(mergedVertex.getLabels(), mergedVertex);
            return mergedVertex;
        }
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(of = "labels")
    public static class Vertex {
        private final Set<InitialVertexLabel> labels;
        private final List<InitialVertexLabel> adjacentVertices;

        public static Vertex of(final InitialVertexLabel vertexName, final InitialVertexLabel... adjacentVertices) {
            return of(newHashSet(vertexName), newArrayList(adjacentVertices));
        }
        public static Vertex of(final InitialVertexLabel vertexName, final List<InitialVertexLabel> adjacentVertices) {
            return of(newHashSet(vertexName), adjacentVertices);
        }

        private static Vertex of(
                final Set<InitialVertexLabel> vertexName, final List<InitialVertexLabel> adjacentVertices
        ) {
            return new Vertex(vertexName, adjacentVertices);
        }
    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InitialVertexLabel {
        private final String label;

        public static InitialVertexLabel of(final String label) {
            return new InitialVertexLabel(label);
        }
    }
}
