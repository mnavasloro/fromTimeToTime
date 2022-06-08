/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.ft3.time.annotationHandler;

import java.util.Comparator;

/**
 *
 * @author mnavas
 */

    public class AnnotationComparator implements Comparator<Annotation> {
        @Override
        public int compare(Annotation o1, Annotation o2) {
            return Integer.valueOf(o1.beginIndex).compareTo(Integer.valueOf(o2.beginIndex));
        }
    }
