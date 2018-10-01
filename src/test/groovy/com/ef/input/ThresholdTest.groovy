package com.ef.input

import spock.lang.Specification

class ThresholdTest extends Specification {

    Threshold threshold

    def setup(){
        threshold = new Threshold()
    }

    def 'given threshold without valid format whe validate should return error'(){
        given:
        String[] args = params

        when:
        threshold.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter threshold. The value should be a number and greater than zero'

        where:
        _ | params
        _ | ['--threshold=-1']
        _ | ['--threshold=A']
        _ | ['--threshold=0']
    }

    def 'given threshold without valid format whe extract should return error'(){
        given:
        String[] args = params

        when:
        threshold.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter threshold. The value should be a number and greater than zero'

        where:
        _ | params
        _ | ['--threshold=-1']
        _ | ['--threshold=A']
        _ | ['--threshold=0']
    }

    def 'given null args when extract threshold should return error'() {
        given:
        String[] args = null

        when:
        threshold.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter threshold'

    }

    def 'given null args when validate threshold should return error'() {
        given:
        String[] args = null

        when:
        threshold.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter threshold'

    }


    def 'given threshold with valid format whe extract should be returned'(){
        given:
        String[] args = params

        when:
        def result = threshold.extract(args)

        then:
        result == expected

        where:
        params               | expected
        ['--threshold=1']    | '1'
        ['--threshold=9999'] | '9999'
        ['--threshold=300']  | '300'
    }
}
