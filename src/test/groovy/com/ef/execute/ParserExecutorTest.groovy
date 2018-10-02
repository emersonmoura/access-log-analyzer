package com.ef.execute


import static com.ef.di.DependencyFactory.createInput
import com.ef.input.InputValidator
import spock.lang.Specification

import java.util.stream.Stream

class ParserExecutorTest  extends Specification {

    ParserExecutor parserExecutor
    InputValidator validatorMock = Mock(InputValidator)
    ParserFile fileMock = Mock(ParserFile)

    def setup(){
        parserExecutor = new ParserExecutor(validatorMock, fileMock, createInput())
    }

    def 'given a valid line when startDate match should return IP result'(){
        given:
        String expectedIp = '192.168.234.82'
        String path = '/path/to/file'
        Stream fileContent = Stream.of(
                """2017-01-01 00:00:11.763|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:00:21.164|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString()
        )
        fileMock.lines(path) >> fileContent
        String[] args = ["--accesslog=${path}",'--startDate=2017-01-01.00:00:00','--duration=hourly','--threshold=2']

        when:
        def result = parserExecutor.execute(args)

        then:
        result.count { it == expectedIp } == 1
    }

    def 'given a valid line should consider only IPs with threshold match'(){
        given:
        String expectedIp = '192.168.234.82'
        String path = '/path/to/file'
        Stream fileContent = Stream.of(
                """2017-01-01 00:00:11.763|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:15:21.164|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:30:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString(),
                """2017-01-01 00:35:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString()
        )
        fileMock.lines(path) >> fileContent
        String[] args = ["--accesslog=${path}",'--startDate=2017-01-01.00:00:00','--duration=hourly','--threshold=4']

        when:
        def result = parserExecutor.execute(args)

        then:
        result.count { it == expectedIp } == 1
    }


    def 'lines with time after hourly duration should not be considered'(){
        given:
        String expectedIp = '192.168.234.82'
        String path = '/path/to/file'
        Stream fileContent = Stream.of(
                """2017-01-01 00:00:11.763|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:15:21.164|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:30:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString(),
                """2017-01-01 01:30:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString()
        )
        fileMock.lines(path) >> fileContent
        String[] args = ["--accesslog=${path}",'--startDate=2017-01-01.00:00:00','--duration=hourly','--threshold=4']

        when:
        def result = parserExecutor.execute(args)

        then:
        result.count { it == expectedIp } == 0
    }

    def 'lines with time after daily duration should not be considered'(){
        given:
        String expectedIp = '192.168.234.82'
        String path = '/path/to/file'
        Stream fileContent = Stream.of(
                """2017-01-01 00:00:11.763|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:15:21.164|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:30:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString(),
                """2017-01-02 00:30:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString()
        )
        fileMock.lines(path) >> fileContent
        String[] args = ["--accesslog=${path}",'--startDate=2017-01-01.00:00:00','--duration=daily','--threshold=4']

        when:
        def result = parserExecutor.execute(args)

        then:
        result.count { it == expectedIp } == 0
    }


    def 'given a valid lines without valid threshold match should not return IPs'(){
        given:
        String expectedIp = '192.168.234.82'
        String path = '/path/to/file'
        Stream fileContent = Stream.of(
                """2017-01-01 00:00:11.763|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:15:21.164|${expectedIp}|"GET / HTTP/1.1"|200|"swcd (unknown version)""".toString(),
                """2017-01-01 00:30:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString(),
                """2017-01-01 00:38:59.410|${expectedIp}|"GET / HTTP/1.1"|200|"Mozilla/5.0""".toString()
        )
        fileMock.lines(path) >> fileContent
        String[] args = ["--accesslog=${path}",'--startDate=2017-01-01.00:00:00','--duration=hourly','--threshold=10']

        when:
        def result = parserExecutor.execute(args)

        then:
        result.isEmpty()
    }
}
