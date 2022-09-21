package cherry.gamebox.tzfe.constant

import com.badlogic.gdx.graphics.Color

/**
 * Constant
 *
 * @author john
 * @since 2022-09-21
 */
object Constant {
    /** 固定世界宽度为 480, 高度根据实际屏幕比例换算  */
    const val FIX_WORLD_WIDTH = 480f

    /** 对话框的宽度  */
    const val DIALOG_WIDTH = 370f

    /** 纹理图集 文件路径  */
    const val ATLAS_PATH = "atlas/game.atlas"

    /** 位图字体 文件路径  */
    const val BITMAP_FONT_PATH = "font/bitmap_font.fnt"

    /** 背景颜色  */
    val BG_RGBA: Color = Color(-0x11182b01)

    /**
     * 纹理图集的小图名称常量
     */
    object AtlasNames {
        const val GAME_BLANK = "game_blank"
        const val GAME_BTN_EXIT = "game_btn_exit"
        const val GAME_BTN_HELP = "game_btn_help"
        const val GAME_HELP_BG = "game_help_bg"
        const val GAME_LOGO = "game_logo"
        const val GAME_RECT_BG = "game_rect_bg"
        const val GAME_ROUND_RECT = "game_round_rect"
        const val GAME_SCORE_BG_BEST = "game_score_bg_best"
        const val GAME_SCORE_BG_NOW = "game_score_bg_now"
        const val DIALOG_BTN_OK = "dialog_btn_ok"
        const val DIALOG_BTN_cancel = "dialog_btn_cancel"
        const val BTN_BACK = "btn_back"
        const val BTN_AGAIN = "btn_again"
    }

    /**
     * 音频资源
     */
    object Audios {
        const val MOVE = "audio/move.mp3"
        const val MERGE = "audio/merge.mp3"
    }

    /**
     * 不同数字的卡片背景颜色
     */
    object CardColors {
        val RGBA_0: Color = Color(-0x333f4c01)
        val RGBA_2: Color = Color(-0x111b2501)
        val RGBA_4: Color = Color(-0x121f3701)
        val RGBA_8: Color = Color(-0xd4e8601)
        val RGBA_16: Color = Color(-0xb6a9c01)
        val RGBA_32: Color = Color(-0xa86b201)
        val RGBA_64: Color = Color(-0xaa2c801)
        val RGBA_128: Color = Color(-0x11179c01)
        val RGBA_256: Color = Color(-0x124fb201)
        val RGBA_512: Color = Color(-0x134fb201)
        val RGBA_1024: Color = Color(-0x146bc801)
        val RGBA_2048: Color = Color(-0x1587de01)
    }

    /**
     * Preferences 键值对存储的相关常量
     */
    object Prefs {
        /** Preferences 键值对存储文件名称  */
        const val FILE_NAME = "game_preferences"

        /** 存储字段名的 key: 最佳分数  */
        const val KEY_BEST_SCORE = "best_score"
    }
}