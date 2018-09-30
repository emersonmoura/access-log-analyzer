package com.ef

import com.ef.input.Duration
import com.ef.input.ParserInput
import com.ef.input.StartDate
import com.ef.input.Threshold
import spock.lang.Specification

class InputValidatorTest extends Specification{

    InputValidator validator

    def setup() {
        List<ParserInput> inputs = Arrays.asList(new Duration(), new StartDate(), new Threshold())
        validator = new InputValidator(inputs)
    }

    def 'when params are not informed should return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'You need to inform someone parameter'

        where:
        _ | params
        _ | ['']
        _ | []
        _ | null
        _ | [null]
        _ | ['', '']
    }

    def 'given params without two hyphens should return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter'

        where:
        _ | params
        _ | ['-startDate']
        _ | ['-duration']
        _ | ['threshold']
        _ | ['threshold=100']
        _ | ['-threshold=100']
    }

    def 'given valid params should not return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
       notThrown(Exception)

        where:
        _ | params
        _ | ['--startDate=2017-01-01.13:00:00']
        _ | ['--duration=hourly']
        _ | ['--threshold=100']

    }

    def 'given both duration param hourly and daily should not return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--duration=hourly']
        _ | ['--duration=daily']
    }

    def 'given duration param not equal both hourly and daily should return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter duration. Should be hourly or daily'

        where:
        _ | params
        _ | ['--duration=minutely']
        _ | ['--duration=weekly']
    }

    def 'given startDate without valid format should return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter startDate. The format Should be yyyy-MM-dd.HH:mm:ss'

        where:
        _ | params
        _ | ['--startDate=2015-08-10']
        _ | ['--startDate=09/10/2018']
        _ | ['--startDate=2019-01-01.13:00']
    }

    def 'given threshold without valid format should return error'(){
        given:
        String[] args = params

        when:
        validator.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter threshold. The value should be a number and greater than zero'

        where:
        _ | params
        _ | ['--threshold=A']
        _ | ['--threshold=0']
    }


}
