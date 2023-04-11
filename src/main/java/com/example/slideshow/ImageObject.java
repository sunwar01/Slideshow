package com.example.slideshow;

public class ImageObject {


        private String filepath;
        private int r;
        private int g;
        private int b;
        private int m;



        public ImageObject(String filepath, int r, int g, int b, int m) {
            this.filepath = filepath;
            this.r = r;
            this.g = g;
            this.b = b;
            this.m = m;

        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getM() {
            return m;
        }

        public void setM(int m) {
            this.m = m;
        }





}
