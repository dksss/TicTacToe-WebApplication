#!/bin/bash
docker rmi -f tictactoe/jodyvole:1.0 && docker build . -t tictactoe/jodyvole:1.0 && \
docker run -d -p 8080:8080 tictactoe/jodyvole:1.0