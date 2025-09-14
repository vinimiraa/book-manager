-- ======================================================
-- Script de criação das tabelas do banco de dados SQLite
-- Projeto: Rubix Book Manager
-- Autor: Vinicius Miranda de Araujo
-- ======================================================

-- Tabela de livros
CREATE TABLE IF NOT EXISTS livro (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    preco REAL NOT NULL,
    editora VARCHAR(255),
    dataPublicacao DATE,
    isbn INTEGER
);