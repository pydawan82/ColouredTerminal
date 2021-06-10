package com.pydawan.rainbowTerm;

public class Configuration {

    private Color foreground = Color.WHITE;
    private Color background = Color.BLACK;
    private boolean bold = false;

    public Color foreground() {
        return foreground;
    }

    public Color background() {
        return background;
    }

    public boolean bold() {
        return bold;
    }
    
    public class Builder {
        Configuration config;

        public Builder() {
            config = new Configuration();
        }
        
        public Configuration build() {
            return config;
        }

        public Builder foreground(Color foreground) {
            config.foreground = foreground;
            return this;
        }

        public Builder background(Color background) {
            config.background = background;
            return this;
        }

        public Builder bold(boolean bold) {
            config.bold = bold;
            return this;
        }
    }
}
