package com.ef.input


import spock.lang.Specification

class DurationTest extends Specification {

    Duration duration

    def setup(){
        duration = new Duration()
    }

    def 'given both duration param hourly and daily when validate should not return error'(){
        given:
        String[] args = params

        when:
        duration.validate(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--duration=hourly']
        _ | ['--duration=daily']
    }

    def 'given duration param not equal both hourly and daily when validate should return error'(){
        given:
        String[] args = params

        when:
        duration.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter duration. Should be hourly or daily'

        where:
        _ | params
        _ | ['--duration=minutely']
        _ | ['--duration=weekly']
    }

    def 'given both duration param hourly and daily when extract should not return error'(){
        given:
        String[] args = params

        when:
        duration.extract(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--duration=hourly']
        _ | ['--duration=daily']
    }

    def 'given args without duration param when extract should not return error'(){
        given:
        String[] args = params

        when:
        duration.extract(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--duratio=hourly']
        _ | ['--startDate=daily']
    }

    def 'given args without duration param when validate should not return error'(){
        given:
        String[] args = params

        when:
        duration.validate(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--duratio=hourly']
        _ | ['--startDate=daily']
    }

    def 'given duration param not equal both hourly and daily when extract should return error'(){
        given:
        String[] args = params

        when:
        duration.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter duration. Should be hourly or daily'

        where:
        _ | params
        _ | ['--duration=minutely']
        _ | ['--duration=weekly']
    }

    def 'given both duration param hourly and daily should extract value'(){
        given:
        String[] args = params

        when:
        def result = duration.extract(args)

        then:
        result == expected

        where:
        params                | expected
        ['--duration=hourly'] | 'hourly'
        ['--duration=daily']  | 'daily'
    }

    def 'given null args when extract duration should return error'() {
        given:
        String[] args = null

        when:
        duration.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter duration'

    }

    def 'given null args when validate duration should return error'() {
        given:
        String[] args = null

        when:
        duration.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter duration'

    }
}
