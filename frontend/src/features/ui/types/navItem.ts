import MaterialIcons from "@expo/vector-icons/MaterialIcons";
import { Href } from "expo-router";

export interface NavItem {
    label: string;
    href: Href;
    icon: keyof typeof MaterialIcons.glyphMap;
}
