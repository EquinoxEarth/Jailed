package io.github.equinoxearth.jailed.config;

/**
 * Baseline for how configs will be setup. This includes the list of guards and jails, as well as
 * default plugin settings
 */
public class Config {
    /*
     * My intentions are to have 3 separate configs, one for the plugin (JailedConfig), one for a list of
     * guards (GuardConfig) and one for a list of jails (JailConfig).
     *
     * The Guard config needs to store each entry as follows:
     *      <UUID>:
     *          <Name>:
     *          <Player>:
     *              <Armor>:
     *              <Inventory>:
     *          <Guard>:
     *              <Armor>:
     *              <Inventory>:
     *
     * The Jail config needs to store each entry as follows:
     *      <JailName>:
     *          <Pos1>:
     *          <Pos2>:
     *          <SpawnPoint>:
     *          <ExitPoint>:
     */
}
