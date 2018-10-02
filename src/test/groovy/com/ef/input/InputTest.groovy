package com.ef.input

import com.ef.di.DependencyFactory
import spock.lang.Specification

class InputTest extends Specification {

    ConsoleInput input

    def setup(){
        input = DependencyFactory.createConsoleInput()
    }

    def 'given valid args with access log should return access log path in mapping values'(){
        given:
        String logPath='/path/'
        String startDate = '2017-01-01.00:00:00'
        String duration='hourly'
        String[] args = ["--accesslog=${logPath}","--startDate=${startDate}","--duration=${duration}"]

        when:
        def result = input.extract(args)

        then:
        result.get(InputType.ACCESS_LOG) == logPath
    }

    def 'given valid args with start date should return start date in mapping values'(){
        given:
        String startDate = '2017-01-01.00:00:00'
        String duration='hourly'
        String threshold='100'
        String[] args = ["--startDate=${startDate}","--duration=${duration}","--threshold=${threshold}"]

        when:
        def result = input.extract(args)

        then:
        result.get(InputType.START_DATE) == startDate
    }

    def 'given valid args with duration should return duration in mapping values'(){
        given:
        String duration='hourly'
        String threshold='100'
        String[] args = ["--duration=${duration}","--threshold=${threshold}"]

        when:
        def result = input.extract(args)

        then:
        result.get(InputType.DURATION) == duration
    }

    def 'given valid args with threshold should return threshold in mapping values'(){
        given:
        String threshold='100'
        String[] args = ["--threshold=${threshold}"]

        when:
        def result = input.extract(args)

        then:
        result.get(InputType.THRESHOLD) == threshold
    }


    def 'given invalid args should return error'(){
        given:
        String[] args = [param]

        when:
        input.extract(args)

        then:
        def ex = thrown(IllegalStateException)
        ex.message == "Invalid input parameter ${param}"

        where:
        _ | param
        _ | '-startDat'
        _ | '-durtion'
        _ | 'threshld'
    }
}
