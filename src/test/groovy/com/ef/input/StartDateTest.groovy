package com.ef.input

import spock.lang.Specification

class StartDateTest extends Specification {
    StartDate startDate

    def setup(){
        startDate = new StartDate()
    }

    def 'given startDate without valid format when validate should return error'(){
        given:
        String[] args = params

        when:
        startDate.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter startDate. The format Should be yyyy-MM-dd.HH:mm:ss'

        where:
        _ | params
        _ | ['--startDate=2015-08-10']
        _ | ['--startDate=09/10/2018']
        _ | ['--startDate=2019-01-01.13:00']
    }

    def 'given startDate without valid format when extract should return error'(){
        given:
        String[] args = params

        when:
        startDate.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter startDate. The format Should be yyyy-MM-dd.HH:mm:ss'

        where:
        _ | params
        _ | ['--startDate=2015-08-10']
        _ | ['--startDate=09/10/2018']
        _ | ['--startDate=2019-01-01.13:00']
    }


    def 'given null args when extract duration should return error'() {
        given:
        String[] args = null

        when:
        startDate.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter startDate'

    }

    def 'given null args when validate duration should return error'() {
        given:
        String[] args = null

        when:
        startDate.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter startDate'

    }

    def 'given valid startDate param should extract value'(){
        given:
        String[] args = params

        when:
        def result = startDate.extract(args)

        then:
        result == expected

        where:
        params                              | expected
        ['--startDate=2017-01-01.13:00:00'] | '2017-01-01.13:00:00'
        ['--startDate=2018-12-05.13:00:00'] | '2018-12-05.13:00:00'
    }


}
