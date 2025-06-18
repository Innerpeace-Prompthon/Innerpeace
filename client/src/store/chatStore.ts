import { create } from "zustand"
import { persist } from "zustand/middleware"
import type { ChatStore, Chat } from "../types/chat"

export const useChatStore = create<ChatStore>()(
  persist(
    (set, get) => ({
      chats: [],
      currentChatId: null,
      sidebarOpen: true,
      isLoading: false,
      showSplitView: false, // 분할 뷰 상태 추가

      addChat: (chat: Chat) => {
        set((state) => ({
          chats: [chat, ...state.chats],
          currentChatId: chat.id,
        }))
      },

      updateChat: (chatId: string, updates: Partial<Chat>) => {
        set((state) => ({
          chats: state.chats.map((chat) => (chat.id === chatId ? { ...chat, ...updates } : chat)),
        }))
      },

      deleteChat: (chatId: string) => {
        set((state) => {
          const newChats = state.chats.filter((chat) => chat.id !== chatId)
          const newCurrentChatId =
            state.currentChatId === chatId ? (newChats.length > 0 ? newChats[0].id : null) : state.currentChatId

          return {
            chats: newChats,
            currentChatId: newCurrentChatId,
          }
        })
      },

      setCurrentChat: (chatId: string | null) => {
        set({ currentChatId: chatId })
      },

      toggleSidebar: () => {
        set((state) => ({ sidebarOpen: !state.sidebarOpen }))
      },

      setSidebarOpen: (open: boolean) => {
        set({ sidebarOpen: open })
      },

      setLoading: (loading: boolean) => {
        set({ isLoading: loading })
      },

      setSplitView: (show: boolean) => {
        set({ showSplitView: show })
      }, // 분할 뷰 설정 함수 추가
    }),
    {
      name: "chat-storage",
    },
  ),
)
