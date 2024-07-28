package com.josemarcellio.ishowbird.models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
    private final List<Particle> particles;

    public ParticleSystem() {
        particles = new ArrayList<>();
    }

    public void emit(double x, double y) {
        for (int i = 0; i < 5; i++) { // loop 5 partikel
            particles.add(new Particle(x, y));
        }
    }

    public void update(double deltaTime) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(deltaTime);
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Particle particle : particles) {
            particle.render(gc);
        }
    }
}
