class Geckodriver < Formula
  desc "WebDriver <-> Marionette proxy"
  homepage "https://github.com/mozilla/geckodriver"
  url "https://github.com/mozilla/geckodriver/archive/v0.24.0.tar.gz"
  sha256 "e6f86b3b6411f078c0a762f978c00ee99926463036a68be01d111bd91f25340e"
  head "https://hg.mozilla.org/mozilla-central/", :using => :hg

  depends_on "rust" => :build

  def install
    dir = build.head? ? "testing/geckodriver" : "."
    cd(dir) { system "cargo", "install", "--root", prefix, "--path", "." }
    bin.install_symlink bin/"geckodriver" => "wires"
  end

  test do
    system bin/"geckodriver", "--help"
  end
end
