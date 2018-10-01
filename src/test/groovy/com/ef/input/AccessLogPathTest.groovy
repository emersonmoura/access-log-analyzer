package com.ef.input

import spock.lang.Specification

class AccessLogPathTest extends Specification{

    AccessLogPath accessLogPath

    def setup(){
        accessLogPath = new AccessLogPath()
    }

    def 'given valid path when validate should not return error'(){
        given:
        String[] args = params

        when:
        accessLogPath.validate(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--accesslog=/path/to/file']
        _ | ['--accesslog=.']
        _ | ['--accesslog=/']
        _ | ['--accesslog=./']
    }

    def 'given valid path when extract should not return error'(){
        given:
        String[] args = params

        when:
        accessLogPath.extract(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--accesslog=/path/to/file']
        _ | ['--accesslog=.']
        _ | ['--accesslog=/']
        _ | ['--accesslog=./']
    }

    def 'given invalid path when validate should not return error'(){
        given:
        String[] args = params

        when:
        accessLogPath.validate(args)

        then:
        notThrown(Exception)

        where:
        _ | params
        _ | ['--accesslog=\\.']
        _ | ['--accesslog=//']
        _ | ['--accesslog=\\']
    }


    def 'given null args when extract duration should return error'() {
        given:
        String[] args = null

        when:
        accessLogPath.extract(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter accessLogPath'

    }


    def 'given null args when validate duration should return error'() {
        given:
        String[] args = null

        when:
        accessLogPath.validate(args)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid parameter accessLogPath'

    }
}
