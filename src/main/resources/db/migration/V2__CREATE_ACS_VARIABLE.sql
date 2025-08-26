-- Create the ACS variables table
CREATE TABLE acs_variable (
                               name TEXT PRIMARY KEY,
                               label TEXT NOT NULL,
                               concept TEXT NOT NULL,
                               document_tsv tsvector,
                               embedding vector(1536)  -- optional: store vector embeddings for semantic search
);

-- Full-text search GIN index
CREATE INDEX idx_acs_variable_tsv
    ON acs_variable USING GIN(document_tsv);

-- Optional trigram GIN index for fuzzy search
CREATE INDEX idx_acs_variable_label_trgm
    ON acs_variable USING GIN(label gin_trgm_ops);
