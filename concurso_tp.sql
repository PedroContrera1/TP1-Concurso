-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 08-09-2021 a las 20:09:46
-- Versión del servidor: 5.5.24-log
-- Versión de PHP: 5.4.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `concurso_tp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_inscripciones`
--

CREATE DATABASE IF NOT EXISTS concurso_tp;

USE concurso_tp;

CREATE TABLE registro_inscripciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    id_participante VARCHAR(50) NOT NULL,
    id_concurso VARCHAR(50) NOT NULL,
    CONSTRAINT uk_participante_concurso UNIQUE (id_participante, id_concurso)
);