{ pkgs ? import <nixpkgs> {} }:

let
  pkgs = import <nixpkgs> { overlays = [ (self: super: {
    jdk = super.adoptopenjdk-jre-openj9-bin-11;
  }) ]; };
in
with pkgs;
pkgs.mkShell {
  buildInputs = [
    leiningen
  ];
}
