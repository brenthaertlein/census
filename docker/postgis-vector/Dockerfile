FROM postgis/postgis:16-3.4

# Install build tools and clang-13
RUN apt-get update && \
    apt-get install -y git build-essential clang-13 postgresql-server-dev-16 && \
    rm -rf /var/lib/apt/lists/*

# Clone and build pgvector
RUN git clone https://github.com/pgvector/pgvector.git /tmp/pgvector && \
    cd /tmp/pgvector && make && make install && \
    rm -rf /tmp/pgvector
