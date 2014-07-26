/*******************************************************************************
 * Copyright (C) 2014 Travis Ralston (turt2live)
 *
 * This software is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.turt2live.hurtle.uuid;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

// TODO: Document
public class UUIDUtils {

    public static UUID getUUID(String name) {
        if (name == null) return null;
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        if (player != null && player.getUniqueId() != null) {
            return player.getUniqueId();
        }
        return UUIDServiceProvider.getUUID(name);
    }

    public static UUID getUUID(CommandSender sender) {
        if (sender == null) return null;
        if (sender instanceof OfflinePlayer) return ((OfflinePlayer) sender).getUniqueId();
        return null;
    }
}
